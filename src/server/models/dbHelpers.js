// db config
const knex = require('knex');
const config = require('../knexfile');
const db = knex(config.development);

module.exports = {
    create,
    read,
    update,
    del
}

//queries
function read(table, flag, id){
    if(flag == 'all')
        return db(table);
    else if(id !== undefined)
        return db(table).where({id});
}

async function create(table, data){
    const res = await db(table).insert(data);
    return res;
}

function update(){

}

function del(table, flag, id){
    if(flag != 'all')
        return db(table).where({ id }).del();
}
