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
        async (trx) => {
            var [t] = await db('users')
                .where('username', data['username'])
                .select('id')
                .transacting(trx);

            user_id = t.id;

            [t] = await db('realtors')
                .insert({
                    user_id: user_id,
                    natural_person: data['natural_person']
                })
                .select('last_insert_rowid() AS id')
                .transacting(trx);

            id = t;

            console.log("REALTOR ID: " + id);

            if (data['natural_person'] == 0) {
                await db('realtorsLegalEntities').insert({
                    id: id,
                    name: data['name'],
                    corporate_address: data['corporate_address'],
                    website_url: data['website_url'],
                    avatar_url: data['avatar_url']
                })
                    .transacting(trx);
            }
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
    return { id };
}

function del(username) {
    return db('realtors').where({ username }).del();
}