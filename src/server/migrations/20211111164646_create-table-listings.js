
exports.up = function(knex) {
    return knex.schema.createTable("listings", (tbl) => {
        tbl.increments() // id
        tbl.text('title')
        tbl.text('approx_address').notNullable()
        tbl.text('exact_address').notNullable()
        tbl.boolean('negotiable').notNullable()
        tbl.text('description', 512)
        tbl.json('amenities')
        tbl.text('username').notNullable()
            .references('username').inTable('users').onDelete('CASCADE').onUpdate('CASCADE')
    })
    .createTable("listings-and-realties", (tbl) => {
        tbl.integer('listing').unsigned().notNullable()
            .references('id').inTable('listings')
            .onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('realty').unsigned().notNullable()
            .references('id').inTable('realties')
           .onDelete('CASCADE').onUpdate('CASCADE') 
    })
    
};

exports.down = function(knex) {
    return knex.schema.dropTableIfExists("listings").dropTableIfExists("listings-and-realties");
};
