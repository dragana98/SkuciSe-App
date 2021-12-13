const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    createUserToRealty,
    readUserToRealtyByPropertyId,
    delUserToRealtyByReviewId,
    readRealtorToUserByUserId,
    createRealtorToUser,
    delRealtorToUserByReviewId,
    realtorsResponse
}

function readUserToRealtyByPropertyId(property_ad_id) {
    return db('reviewsUserToRealty').where({ property_ad_id });
}

async function createUserToRealty(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            return db('reviewsUserToRealty').insert({
                reviewer_id: user_id,
                property_ad_id: data['property_ad_id'],
                date: new Date().toISOString(),
                stars: data['stars'],
                contents: data['contents'],
                response: null,
                response_date: null
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

function readRealtorToUserByUserId(user_id) {
    return db('reviewsRealtorToUser').where({ user_id });
}

async function createRealtorToUser(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            var [realtor_id] = await db('realtors')
                .where({ user_id })
                .select('id')
                .transacting(trx);

            realtor_id = realtor_id.id;

            return db('reviewsRealtorToUser').insert({
                realtor_id: realtor_id,
                user_id: data['user_id'],
                date: new Date().toISOString(),
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

async function realtorsResponse(data) {
    db.transaction(
        async (trx) => {
            var [user_id] = await db('users')
                .where({ username: data['username'] })
                .select('id')
                .transacting(trx);

            user_id = user_id.id;

            return db('reviewsUserToRealty')
                .where({ id: data['id'] })
                .update({
                    response: data['response'],
                    response_date: new Date().toISOString()
                })
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