const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    createUserToRealty,
    readUserToRealtyByPropertyId,
    delUserToRealtyByReviewId,
    readRealtorToUserByUserId,
    createRealtorToUser,
    delRealtorToUserByReviewId
}

function readUserToRealtyByPropertyId(property_ad_id){
    return db('reviewsUserToRealty').where({ property_ad_id });
}

async function createUserToRealty(data) {
    db.transaction(
        (trx) => {
            const user_id = await db('users')
            .where({username: data['username']})
            .select('id')
            .first();

            return db('reviewsUserToRealty').insert({
                reviewer_id: user_id,
                property_ad_id: data['property_ad_id'],
                date: new Date().toISOString(),
                stars: data['stars'],
                contents: data['contents'],
                response: data['response'],
                response_date: data['response_date']
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


function delUserToRealtyByReviewId(id) {
    return db('reviewsUserToRealty').where({ id }).del();
}

function readRealtorToUserByUserId(user_id){
    return db('reviewsRealtorToUser').where({ user_id });
}

async function createRealtorToUser(data) {
    db.transaction(
        (trx) => {
            const user_id = await db('users')
            .where({username: data['username']})
            .select('id')
            .first();

            const realtor_id = await db('realtors')
            .where({user_id})
            .select('id')
            .first();


            return db('reviewsRealtorToUser').insert({
                realtor_id: realtor_id,
                user_id: data['user_id'],
                date: data['date'],
                recommends: data['recommends'],
                contents: data['contents']
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

function delRealtorToUserByReviewId(id) {
    return db('reviewsRealtorToUser').where({ id }).del();
}