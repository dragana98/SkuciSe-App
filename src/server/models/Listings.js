// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);
const Favorites = require('./Favorites');

module.exports = {
    create,
    del,
    readAll,
    readAllByUserId,
    readByPropertyId,
    search
}

async function search(data) {
    return db("propertyAd")
        .where({ leasable: data["leasable"] })
        .andWhere({ unified: data["unified"] })
        .andWhere('city', 'in', data["cities"])
        .select('id');
}

async function readByPropertyId(data) {
    try {
        const id = data.id;
        var listing = await db("propertyAd")
            .where('id', '=', id)
            .select()
            .first()

        listing.images = []
        listing.amenities = []

        var t = await db('propertyAdImages')
            .where('property_ad_id', '=', id)
            .select('url')

        t.forEach(v => {
            listing.images.push(v.url)
        })

        t = await db('propertyAdAmenities')
            .where('property_ad_id', '=', id)
            .select('amenity')

        t.forEach(v => {
            listing.amenities.push(v.amenity)
        })

        if (listing.unified != 0) {
            if (listing.leasable == 0) {
                /* SELLABLE */
                t = await db('sellable')
                    .where('property_ad_id', '=', id)
                    .select('price')
                    .first();
                listing.price = t.price
            } else {
                /* LEASABLE */
                t = await db('leasable')
                    .where('property_ad_id', '=', id)
                    .select('price', 'deposit')
                    .first();
                listing.price = t.price
                listing.deposit = t.deposit
            }
        }

        var realties = await db('propertyAdRealties')
            .where('property_ad_id', '=', id)
            .select()

        for (var i = 0; i < realties.length; i++) {
            var realty = realties[i]

            if (listing.unified == 0) {
                if (listing.leasable == 0) {
                    /* SELLABLE */
                    t = await db('sellable')
                        .where('property_ad_realty_id', '=', realty.id)
                        .select('price')
                        .first();
                    realty.price = t.price
                } else {
                    /* LEASABLE */

                    t = await db('leasable')
                        .where('property_ad_realty_id', '=', realty.id)
                        .select('price', 'deposit')
                        .first();
                    realty.price = t.price
                    realty.deposit = t.deposit

                }
            }
        }

        t = await Favorites.read(data.username);

        var inArray = false;

        for (var i = 0; i < t.length; i++) {
            if (t[i].property_ad_id == id) {
                inArray = true;
            }
        }

        if (inArray) {
            listing.is_favorite = true;
        } else {
            listing.is_favorite = false;
        }

        listing.property_ad_realties = realties

        return listing;
    }
    catch (e) {
        console.log(e)
    }
}

async function create(data) {
    var id = -1;

    try {
        db.transaction(async trx => {
            // Insert basic, common information regardless of type of the realty
            var [propertyId] = await db('propertyAd')
                .insert({
                    name: data['name'],
                    realtor_id: data['realtor_id'],
                    description: data['description'],
                    city: data['city'],
                    postal_code: data['postal_code'],
                    street_address: data['street_address'],
                    leasable: data['leasable'],
                    unified: data['unified']
                })
                .select('SELECT last_insert_rowid() AS id')
                .transacting(trx);

            id = propertyId;

            // Images are common for all 
            for (var i = 0; i < data['images'].length; i++) {
                var imageUrl = data['images'][i]
                await db('propertyAdImages').insert({
                    property_ad_id: propertyId,
                    url: imageUrl
                }).transacting(trx);

            }

            for (var i = 0; i < data['amenities'].length; i++) {
                var amenity = data['amenities'][i]
                await db('propertyAdAmenities').insert({
                    property_ad_id: propertyId,
                    amenity: amenity
                }).transacting(trx);
            }

            if (data['unified'] != '0') {
                if (data['leasable'] == 0) {
                    /* SELLABLE */
                    await db('sellable').insert({
                        property_ad_id: propertyId,
                        property_ad_realty_id: null,
                        price: data['price']
                    }).transacting(trx);
                } else {
                    /* LEASABLE */
                    await db('leasable').insert({
                        property_ad_id: propertyId,
                        property_ad_realty_id: null,
                        price: data['price'],
                        deposit: data['deposit']
                    }).transacting(trx);
                }
            }

            for (var i = 0; i < data['property_ad_realties'].length; i++) {
                var realty = data['property_ad_realties'][i]
                var [realtyId] = await db('propertyAdRealties')
                    .insert({
                        property_ad_id: propertyId,
                        number_of_rooms: realty['number_of_rooms'],
                        number_of_bathrooms: realty['number_of_bathrooms'],
                        surface: realty['surface'],
                        floor_plan_url: realty['floor_plan_url'],
                    })
                    .select('SELECT last_insert_rowid() AS id')
                    .transacting(trx);

                if (data['unified'] == 0) {
                    if (data['leasable'] == 0) {
                        /* SELLABLE */
                        await db('sellable').insert({
                            property_ad_id: null,
                            property_ad_realty_id: realtyId,
                            price: realty['price']
                        }).transacting(trx);
                    } else {
                        /* LEASABLE */
                        await db('leasable').insert({
                            property_ad_id: null,
                            property_ad_realty_id: realtyId,
                            price: realty['price'],
                            deposit: realty['deposit']
                        }).transacting(trx);
                    }
                }
            }
        }).then(() => {
            console.log('transaction complete');
        })
    }
    catch (err) { console.log(err) }

    if (id != -1) {
        return { id };
    }
}
function del(id) {
    return db("propertyAd").where({ id }).del();
}
async function readAll(data) {
    var ids = await db('propertyAd').select('id')
    var result = []

    try {
        for (var i = 0; i < ids.length; i++) {
            var _data = ids[i];
            _data.username = data.username;
            var t = await readByPropertyId(_data);
            result.push(t)
        }
    } catch (e) {
        console.log(e);
    }
    return result
}

async function readAllByUserId(data) {
    var user_id = await db('users').where({ username: data['username'] }).select('id').first();
    user_id = user_id.id;
    var realtor_id = await db('realtors').where({ user_id }).select('id').first();
    realtor_id = realtor_id.id;

    var ads = await db('propertyAd').where({ realtor_id }).select('id');
    var result = [];

    for (i = 0; i < ads.length; i++) {
        const property_id = ads[i].id;
        data.id = property_id;
        result.push(await readByPropertyId(data));
    }

    return result;
}