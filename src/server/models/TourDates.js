const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    read,
    readAll,
    schedule
}

function read(property_ad_id) {
    return db('availableTourDates')
        .where({ property_ad_id })
        .andWhere('scheduled_at', 'IS', null)
        .andWhere('date', '>', new Date().toISOString())
        .select('id', 'date');
}

function readAll(property_ad_id) {
    return db('availableTourDates')
        .where({ property_ad_id });
}

async function create(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            return db('availableTourDates')
                .insert({
                    property_ad_id: data['property_ad_id'],
                    date: data['date'],
                    scheduled_by_user_id: null,
                    scheduled_at: null
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

async function schedule(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            return db('availableTourDates')
                .where({
                    id: data["tour_id"]
                })
                .update({ 
                    scheduled_by_user_id: user_id,
                    scheduled_at: new Date().toISOString() })
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


function del(property_ad_id) {
    return db('availableTourDates').where({ property_ad_id }).del();
}