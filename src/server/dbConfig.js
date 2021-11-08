const dbEnginge = process.env.DB_ENV || 'development';
const config = require('./knexfile')[dbEnginge]

module.exports = require('knex')(config)