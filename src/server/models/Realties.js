// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    del,
    readAll,
    read
}

async function create(realty){
    const [id] = await db("realties").insert( realty );
    return id;
}
function del(id){
    return db("realties").where({ id }).del();
}
function readAll(){
    return db("realties");
}
function read(id){
    return db("realties").where({ id });
}