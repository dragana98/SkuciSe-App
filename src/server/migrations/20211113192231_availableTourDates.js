exports.up = function (knex) {
    return knex.schema.createTable('availableTourDates', (tbl) => {
        tbl.increments()
        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.text('date', 128).notNullable()

        tbl.integer('scheduled_by_user_id')
            .references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.text('scheduled_at', 128).notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('availableTourDates');
};
