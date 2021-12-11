const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    read
}

function read(user_id){
    return db('favorites').where({ user_id }).first();
}

async function getFavorites(username) {
    const { id } = await db('users').where({ username }).first();
    knex.where().first()
    return db("favorites as f").join("realties as r", "f.realty_id", "r.id").where({ user_id: id });
}


async function create(data) {
    db.transaction(
        (trx) => {
            return db('favorites').insert({
                property_ad_id: data['property_ad_id'],
                user_id: data['user_id']
            }).transacting(trx)
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

function del(user_id){
    return db('favorites').where({ user_id }).del();
}