// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    push,
    readAll,
    markConversationAsRead,
    preview
}

async function push(data) {
    var id

    await db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            var [t] = await db('messages')
                .insert({
                    usnd: user_id,
                    urcv: data['urcv'],
                    contents: data['contents'],
                    read: "0",
                    date: new Date().toISOString()
                })
                .select('last_insert_rowid() as id')
                .transacting(trx);
            id = t;
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
    return { id }
}

function readAll(uid) {
    return db('messages')
        .where('usnd', uid)
        .orWhere('urcv', uid)
        .orderBy('date');
}

async function preview(username) {
    var { user_id } = await db('users')
        .where({ username })
        .select('id AS user_id')
        .first();

    var t = await db('messages')
        .select('usnd as id')
        .where(function () {
            this
                .where({ usnd: user_id })
                .orWhere({ urcv: user_id })
        })
        .andWhere('usnd', '!=', user_id)
        .union([
            db('messages')
                .select('urcv as id')
                .where(function () {
                    this
                        .where({ usnd: user_id })
                        .orWhere({ urcv: user_id })
                })
                .andWhere('urcv', '!=', user_id)
        ]);

    var result = [];

    for (var i = 0; i < t.length; i++) {
        const conv_id = t[i].id;

        var [last_msg] = await db('messages')
            .where(function () {
                this.where('usnd', conv_id)
                    .orWhere('urcv', user_id)
            })
            .orWhere(function () {
                this.where('usnd', user_id)
                    .orWhere('urcv', conv_id)
            })
            .max('date')

        last_msg = last_msg["max(`date`)"];

        var q = await db('messages')
            .where({ date: last_msg })
            .first();

        result.push(q);
    }

    return result;
}

function markConversationAsRead(data) {
    return db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            await db('messages')
                .where({ urcv: user_id })
                .andWhere({ usnd: data['usnd'] })
                .update({ read: 1 })
                .transacting(trx);
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
}