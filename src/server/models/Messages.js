// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    push,
    readAll
}

async function push(data) {
    var id

    await db.transaction(
        (trx) => {
            db('messages').insert({
                usnd: data['usnd'],
                urcv: data['urcv'],
                contents: data['contents'],
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
    return db('messages').where('usnd', uid).orWhere('urcv', uid).orderBy('date');
}