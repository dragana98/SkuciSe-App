
exports.up = function (knex) {
    return knex.schema.createTable("propertyAdImages", (tbl) => {
        tbl.increments() //id primary key AI
        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.text('url', 255).notNullable()
    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('propertyAdImages');
};
