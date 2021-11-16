// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    readAll,
    readByPropertyId,
    //readByUsername,
    //leaveReview,
    //getReview
}

async function create(data) {
    try {
        db.transaction(async trx => {
            var propertyId;

            db('propertyAd').insert({
                name: data['name'],
                realtor_id: data['realtor_id'],
                description: data['description'],
                city: data['city'],
                postal_code: data['postal_code'],
                street_address: data['street_address'],
                leasable: data['leasable'],
                unified: data['unified']
            }).transacting(trx).returning('id').then(([id]) => propertyId = id);

            data['images'].forEach(imageUrl => {
                db('propertyAdImages').insert({
                    property_ad_id: propertyId,
                    url: imageUrl
                }).transacting(trx).returning('id').then(([id]) => propertyId = id);
    
            });
            
            data['amenities'].forEach(amenity => {
                db('propertyAdAmenities').insert({
                    property_ad_id: propertyId,
                    amenity: amenity
                }).transacting(trx).returning('id').then(([id]) => propertyId = id);
    
            });
            

            if(data['unified'] != '0') {
                if(data['leasable'] == 0) {
                    /* SELLABLE */
                    db('sellable').insert({
                        property_ad_id: propertyId,
                        property_ad_realty_id: null,
                        price: data['price']
                    }).transacting(trx);
                } else {
                    /* LEASABLE */
                    db('leasable').insert({
                        property_ad_id: propertyId,
                        property_ad_realty_id: null,
                        price: data['price'],
                        deposit: data['deposit']
                    }).transacting(trx);
                }
            }

            data['property_ad_realties'].forEach(realty => {
                var realtyId;

                db('propertyAdRealties').insert({
                    property_ad_id: realty['property_ad_id'],
                    number_of_rooms: realty['number_of_rooms'],
                    number_of_bathrooms: realty['number_of_bathrooms'],
                    surface: realty['surface'],
                    floor_plan_url: realty['floor_plan_url'],
                }).transacting(trx).returning('id').then(([id]) => realtyId = id);

                if(data['unified'] == 0) {
                    if(data['leasable'] == 0) {
                        /* SELLABLE */
                        db('sellable').insert({
                            property_ad_id: null,
                            property_ad_realty_id: realtyId,
                            price: realty['price']
                        }).transacting(trx);
                    } else {
                        /* LEASABLE */
                        db('leasable').insert({
                            property_ad_id: null,
                            property_ad_realty_id: realtyId,
                            price: realty['price'],
                            deposit: realty['deposit']
                        }).transacting(trx);
                    }
                }
            });
        })
    }
    catch (err) { console.log(err) }

}
function del(id) {
    /* TODO */
    return db("propertyAd").where({ id }).del();
}
async function readAll() {
    return db('propertyAd').select('*')

    /* var listings = db("listings")

    for (i = 0; i < listings.length; i++) {
        listings[i]['amenities'] = JSON.parse(listings[i]['amenities'])
        var realties = db('realties as r')
            .join('listings-and-realties as lnr', 'r.id', 'lnr.realty')
            .where({ listing: listings[i]['id'] })

        listings[i]['realties'] = []
        listings[i]['realties'].push(...realties)
    } */
}

// TODO
async function readByPropertyId(id) {
    var listings = db("property").where({ id })

    for (i = 0; i < listings.length; i++) {
        listings[i]['amenities'] = JSON.parse(listings[i]['amenities'])
        var realties = db('realties as r')
            .join('listings-and-realties as lnr', 'r.id', 'lnr.realty')
            .where({ listing: listings[i]['id'] })

        listings[i]['realties'] = []
        listings[i]['realties'].push(...realties)
    }

    return listings;
}
/* 
async function readByUsername(username) {
    var listings = db("listings").where({ username })

    for (i = 0; i < listings.length; i++) {
        listings[i]['amenities'] = JSON.parse(listings[i]['amenities'])
        var realties = db('realties as r')
            .join('listings-and-realties as lnr', 'r.id', 'lnr.realty')
            .where({ listing: listings[i]['id'] })

        listings[i]['realties'] = []
        listings[i]['realties'].push(...realties)
    }

    return listings;
} */
