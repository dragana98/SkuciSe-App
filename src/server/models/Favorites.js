const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create_del,
    read
}

async function read(username) {
    var [user_id] = await db('users')
        .where({ username })
        .select('id');

    user_id = user_id.id;

    return db("favorites")
        .where({ user_id })
        .select('property_ad_id');
}

async function create_del(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;
            
            var [is_fav] = await db('favorites')
                .where({ user_id, property_ad_id: data['property_ad_id'] })
                .count()
                .transacting(trx);

            is_fav = is_fav['count(*)'];

            if (is_fav != '0') {
                return db('favorites')
                    .where({
                        property_ad_id: data['property_ad_id'],
                        user_id: user_id
                    })
                    .del()
                    .transacting(trx);
            } else {
                return db('favorites')
                    .insert({
                        property_ad_id: data['property_ad_id'],
                        user_id: user_id
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
}