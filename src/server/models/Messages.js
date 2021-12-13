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
    return { id }
}

function readAll(uid) {
    return db('messages')
        .where('usnd', uid)
        .orWhere('urcv', uid)
        .orderBy('date');
}

async function preview(uid) {
    var [last_msg] = await db('messages')
        .where('usnd', uid)
        .orWhere('urcv', uid)
        .max('date')

    last_msg = last_msg["max(`date`)"];

    return db('messages')
        .where({ date: last_msg })
        .first();
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