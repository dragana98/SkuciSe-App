// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    readByRealtorId,
    readRealtorLegalEntitiyDataById,
    del
}

function readRealtorLegalEntitiyDataById(id) {
    return db('realtorsLegalEntities').where({ id }).first();
}

function readByRealtorId(id) {
    return db('realtors').where({ id }).first();
}

async function create(data) {
    var id

    await db.transaction(
        (trx) => {
            db('realtors').insert({
                user_id: data['user_id'],
                natural_person: data['natural_person']
            }).transacting(trx)
                .then(([o]) => {
                    id = o
                }).then(trx.commit)
                .catch(trx.rollback);
        })

    if (data['natural_person'] == 0) {
        await db.transaction(
            (trx) => {
                db('realtorsLegalEntities').insert({
                    id: id,
                    name: data['name'],
                    corporate_address: data['corporate_address'],
                    website_url: data['website_url'],
                    avatar_url: data['avatar_url']
                }).transacting(trx).then(trx.commit)
                    .catch(trx.rollback);

            })
    }

    return { id }
}

function del(username) {
    return db('realtors').where({ username }).del();
}