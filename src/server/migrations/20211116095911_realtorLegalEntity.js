
exports.up = function(knex) {
    return knex.schema.createTable('realtorsLegalEntities', (tbl) => {
        tbl.integer('id').primary().notNullable()
        tbl.text('name', 255).notNullable()
        tbl.text('corporate_address', 255).notNullable()
        tbl.text('website_url', 255).notNullable()
        tbl.text('avatar_url', 255).notNullable()
    })
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists('realtorsLegalEntities').dropTableIfExists('realtorsLegalEntities');

};