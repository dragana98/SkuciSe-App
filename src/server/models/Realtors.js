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
            const user_id = await db('users')
                .where('username', data['username'])
                .select('id')
                .first();

            db('realtors').insert({
                user_id: user_id,
                natural_person: data['natural_person']
            })
                .transacting(trx)
                .then(trx.commit)
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
                })
                    .transacting(trx)
                    .then(trx.commit)
                    .catch(trx.rollback);
            })
    }
}

function del(username) {
    return db('realtors').where({ username }).del();
}