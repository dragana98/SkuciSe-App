const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);
const bcrypt = require('bcryptjs');

module.exports = {
    create,
    readData,
    readBasicData,
    readCred,
    update,
    del
}

function readData(username) {
    return db('userData')
        .where({ username })
        .first();
}

function readCred(username) {
    return db('users').where({ username }).first();
}

function readBasicData(id) {
    return db('userData')
        .where({ id })
        .select('name', 'surname', 'username', 'avatar_url', 'phone_number')
        .first();
}

async function create(data) {
    db.transaction(
        (trx) => {
            db('users').insert({
                username: data['username'],
                password: data['password']
            }).transacting(trx)
                .then(() => {
                    return db('userData').insert({
                        username: data['username'],
                        name: data['name'],
                        surname: data['surname'],
                        date_of_birth: data['date_of_birth'],
                        phone_number: data['phone_number'],
                        document_type: data['document_type'],
                        document_number: data['document_number'],
                        avatar_url: data['avatar_url']
                    }).transacting(trx);
                })
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

async function update(data) {
    return db.transaction(
        async (trx) => {
            const { old_username, username, phone_number, password, avatar_url } = data;

            if (avatar_url != null) {

                await db('userData')
                    .where({ username: old_username })
                    .update({ avatar_url })
                    .transacting(trx);
            }
            if (phone_number != null) {

                await db('userData')
                    .where({ username: old_username })
                    .update({ phone_number })
                    .transacting(trx);

            }
            if (username && old_username && (username.valueOf() !== old_username.valueOf())) {
                await db('users')
                    .where({ username: old_username })
                    .update({ username: username })
                    .transacting(trx);
            }

            if (password != null) {
                const hash = bcrypt.hashSync(data.password, 12);
                data.password = hash;
                await db('users')
                    .where({ username: username })
                    .update({ password: data.password })
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

// TODO invalidate tokens and logout user

async function del(username) {
    db.transaction(
        async (trx) => {
            var user_id = await db('users').where({ username }).select('id').first();
            var realtor_id = await db('realtors').where({ username }).select('id').first();
            var all_properties_ids = await db('propertyAd').where({ realtor_id }).select('id');
            var all_realties_ids = await db('propertyAdRealties').whereIn('property_ad_id', all_properties_ids).select('id');

            db('reviewsRealtorToUser')
                .where({ user_id })
                .del();

            db('reviewsRealtorToUser')
                .where({ realtor_id })
                .del();

            db('reviewsUserToRealty')
                .where({ reviewer_id: user_id })
                .del();

            db('reviewsUserToRealty')
                .whereIn('property_ad_id', all_properties_ids)
                .del();

            db('availableTourDates')
                .whereIn('property_ad_id', all_properties_ids)
                .del();

            db('messages')
                .where({ usnd: user_id })
                .orWhere({ usrc: user_id })
                .del();

            db('propertyAdImages')
                .whereIn('property_ad_id', all_properties_ids)
                .del();

            db('propertyAdAmenities')
                .whereIn('property_ad_id', all_properties_ids)
                .del();

            db('leasable')
                .whereIn('property_ad_realty_id', all_realties_ids)
                .orWhereIn('property_ad_id', all_properties_ids)
                .del();

            db('sellable')
                .whereIn('property_ad_realty_id', all_realties_ids)
                .orWhereIn('property_ad_id', all_properties_ids)
                .del();

            db('propertyAdRealties')
                .whereIn('property_ad_id', all_properties_ids)
                .del();

            db('propertyAd')
                .whereIn('id', all_properties_ids)
                .del();

            db('realtorsLegalEntities')
                .where({ id: realtor_id })
                .del();

            db('realtors')
                .where({ user_id })
                .del();

            db('users')
                .where({ id: user_id })
                .del();
        })
        .then(() => {
            console.log('transaction complete');
        })
        .catch(err => {
            console.log(err);
        })
}