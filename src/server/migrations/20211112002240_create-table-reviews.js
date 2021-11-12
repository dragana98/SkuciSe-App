
exports.up = function(knex) {
    return knex.schema.createTable('reviews', (tbl) => {
        tbl.increments()
        tbl.text('text', 512).notNullable()
        tbl.text('reviewed_username').unsigned().references('username')
            .inTable('users').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.text('reviewer_username').unsigned().references('username')
            .inTable('users').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('rating').unsigned().notNullable()
        tbl.date('date').notNullable()
        tbl.text('response')
        tbl.text('response_date')
        tbl.integer('listing').unsigned().notNullable()
            .references('id').inTable('listings')
            .onDelete('CASCADE').onUpdate('CASCADE')
    })
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists('reviews').dropTableIfExists('reviews-for-listings');
};
