
exports.up = function (knex) {
    return knex.schema.createTable("propertyAdAmenities", (tbl) => {
        tbl.increments() //id primary key AI
        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.text('amenity').notNullable()
    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('propertyAdAmenities');
};
