const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    read
}

function read(property_ad_id){
    return db('availableTourDates')
    .where({ property_ad_id })
    .andWhere('date', '>', new Date().toISOString())
    .select();
}

async function create(data) {
    db.transaction(
        (trx) => {
            const user_id = await db('users')
            .where({username: data['username']})
            .select('id')
            .first();

            return db('availableTourDates').insert({
                property_ad_id: data['property_ad_id'],
                date: data['date'],
                scheduled_by_user_id: user_id,
                scheduled_at: new Date().toISOString()
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

function del(property_ad_id){
    return db('availableTourDates').where({ property_ad_id }).del();
}