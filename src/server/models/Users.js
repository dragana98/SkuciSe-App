// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    read,
    readAll,
    update,
    del
}

//queries
function read(username){
    return db('users').where({ username }).first();
}
function readAll(){
    return db('users');
}
async function create(data){
    const res = await db('users').insert(data);
    return res;
}
function update(){

}
function del(id){
    return db(table).where({ id }).del();
}
