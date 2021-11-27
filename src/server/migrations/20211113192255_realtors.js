exports.up = function (knex) {
    return knex.schema.createTable('realtors', (tbl) => {
        tbl.increments()
        tbl.integer('user_id').notNullable()
            .references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.integer('natural_person').notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('realtors').dropTableIfExists('realtorsLegalEntities');
};
