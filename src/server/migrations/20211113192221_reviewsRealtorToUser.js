exports.up = function (knex) {
    return knex.schema.createTable('reviewsRealtorToUser', (tbl) => {
        tbl.increments()
        tbl.integer('realtor_id').notNullable()
            .references('id').inTable('realtors')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.integer('user_id').notNullable()
            .references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.text('date', 128).notNullable()
        tbl.integer('recommends').notNullable()
        tbl.text('contents', 255).notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('reviewsRealtorToUser');
};
