exports.up = function (knex) {
    return knex.schema.createTable('realtors', (tbl) => {
        tbl.increments()
        tbl.integer('user_id').notNullable()
            .references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.integer('natural_person').notNullable()
    })
    .createTable('realtorsLegalEntities', (tbl) => {
        tbl.increments()
        tbl.text('name', 255).notNullable()
        tbl.text('corporate_address', 255).notNullable()
        tbl.text('website_url', 255).notNullable()
        tbl.text('avatar_url', 255).notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('realtors'),dropTableIfExists('realtorsLegalEntities');
};
