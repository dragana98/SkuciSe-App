// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    readData,
    readCred,
    update,
    del,
    getFavorites
}

//queries
function readData(username){
    return db('userData').where({ username }).first();
}
function readCred(username){
    return db('users').where({ username }).first();
}
async function create(data){
    
    db.transaction( 
    (trx) => {
        db('users').insert({
            username: data['username'],
            password: data['password']
        }).transacting(trx)
        .then( () => {
            return db('userData').insert({
                name: data['name'],
                username: data['username']
            }).transacting(trx);
        })
        .then(trx.commit)
        .catch(trx.rollback);
    })
    .then( ()=> {
        console.log('transaction complete');
    })
    .catch( err => {
        console.log(err);
    })
}
function update(username){
    const res = db('userData').where({ username }).update({ username })
    return res;
}
function del(username){
    return db('userData').where({ username }).del();
}
async function getFavorites(username){
    const { id } = await db('users').where({ username }).first();
    knex.where().first()
    return db("favorites as f").join("realties as r", "f.realty_id", "r.id").where({user_id: id});
}
