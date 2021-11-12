// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    readAll,
    read,
    leaveReview,
    getReview
}

async function create(data){
    var lnt = data['realties'].length;
    try{
         await db.transaction( async trx => {
            var arr = [];
            data["realties"].forEach( (data)=>{
                arr.push(data)
            })
            lastRealtyId = await db.batchInsert('realties',arr).transacting(trx)

            listingId = await db('listings').insert({
                title: data['title'],
                approx_address: data['approx_address'],
                exact_address: data['exact_address'],
                negotiable: data['negotiable'],
                description: data['description'],
                amenities: JSON.stringify(data['amenities']),
                username: data['username']
            }).transacting(trx);

           
            for(i=lastRealtyId-0;i>lastRealtyId-lnt;i--){
                await db('listings-and-realties').insert(
                    {
                        listing: listingId,
                        realty: i
                    }
                ).transacting(trx);
            }
        })
    }
    catch(err){console.log(err)}
    
}
function del(id){
    return db("realties").where({ id }).del();
}
async function readAll(){
   
    var listings = await db("listings")
    
    for(i=0; i<listings.length; i++){
        listings[i]['amenities'] = JSON.parse(listings[i]['amenities'])
        var realties = await db('realties as r')
        .join('listings-and-realties as lnr', 'r.id', 'lnr.realty')
        .where({ listing: listings[i]['id']})
        
        listings[i]['realties'] = []
        listings[i]['realties'].push(...realties)
    }
    
    return listings;
}
async function read(username){
    var listings = await db("listings").where({username})
    
    for(i=0; i<listings.length; i++){
        listings[i]['amenities'] = JSON.parse(listings[i]['amenities'])
        var realties = await db('realties as r')
        .join('listings-and-realties as lnr', 'r.id', 'lnr.realty')
        .where({ listing: listings[i]['id']})
        
        listings[i]['realties'] = []
        listings[i]['realties'].push(...realties)
    }
    
    return listings;
}

async function leaveReview(id, body){
    return await db('reviews').insert(body)
}

function getReview(listing){
    return db('reviews').where({ listing })
}