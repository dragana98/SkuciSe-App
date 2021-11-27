const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    read
}

function read(property_ad_id){
    return db('availableTourDates').where({ property_ad_id });
}

async function create(data) {
    db.transaction(
        (trx) => {
            return db('availableTourDates').insert({
                property_ad_id: data['property_ad_id'],
                date: data['date'],
                scheduled_by_user_id: data['scheduled_by_user_id'],
                scheduled_at: data['scheduled_at']
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