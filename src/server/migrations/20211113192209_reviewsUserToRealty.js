
exports.up = function (knex) {
    return knex.schema.createTable('reviewsUserToRealty', (tbl) => {
        tbl.increments()
        tbl.integer('reviewer_id').notNullable()
            .references('id').inTable('users')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd')
            .onDelete('CASCADE').onUpdate('CASCADE')

        tbl.text('date', 128).notNullable()
        tbl.integer('stars').notNullable()

        tbl.text('contents', 255).notNullable()

        tbl.text('response')
        tbl.text('response_date')
    })
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('reviewsUserToRealty');
};
