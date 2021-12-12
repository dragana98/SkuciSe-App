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
        (trx) => {
            const user_id = await db('users')
            .where({username: data['username']})
            .select('id')
            .first();

            db('messages').insert({
                usnd: user_id,
                urcv: data['urcv'],
                contents: data['contents'],
                read: "0",
                date: new Date().toISOString()
            }).transacting(trx)
                .then(([o]) => {
                    id = o
                }).then(trx.commit)
                .catch(trx.rollback);
        })
    return { id }
}

function readAll(uid) {
    return db('messages')
        .where('usnd', uid)
        .orWhere('urcv', uid)
        .orderBy('date');
}

function preview(uid) {
    const last_msg = await db('messages')
    .where('usnd', uid)
    .orWhere('urcv', uid)
    .select('MAX(date) as last_msg')
    
    return db('messages')
        .where({date: last_msg})
        .first();
}

function markConversationAsRead(data) {
    db.transaction(
        (trx) => {
            const user_id = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .first();
            
                await db('messages')
                .where({ urcv: user_id })
                .andWhere({ usnd: data['usnd'] })
                .update({ read: 1 })
                .transacting(trx)
                .then(trx.commit)
                .catch(trx.rollback);
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
}