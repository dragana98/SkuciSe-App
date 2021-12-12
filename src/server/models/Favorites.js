const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create_del,
    read
}

async function read(username) {
    const user_id = await db('users')
        .where({ username })
        .select('id')
        .first();

    return db("favorites")
        .where({ user_id })
        .select('property_id')
}

async function create_del(data) {
    db.transaction(
        (trx) => {
            const user_id = await db(users)
                .where({ username: data['user_id'] })
                .select('id')
                .first();

            const is_fav = await db('favorites')
                .where({ user_id, property_ad_id: data['property_ad_id'] })
                .select('COUNT (*)')
                .first();

            if (is_fav != '0') {
                return db('favorites')
                    .where({
                        property_ad_id: data['property_ad_id'],
                        user_id: user_id
                    })
                    .del()
                    .transacting(trx)
                    .then(trx.commit)
                    .catch(trx.rollback);
            } else {
                return db('favorites')
                    .insert({
                        property_ad_id: data['property_ad_id'],
                        user_id: user_id
                    })
                    .transacting(trx)
                    .then(trx.commit)
                    .catch(trx.rollback);
            }
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
}