
exports.up = function (knex) {
    return knex.schema.createTable("sellable", (tbl) => {
        tbl.increments() //id primary key AI
        tbl.integer('property_ad_realty_id').notNullable()
            .references('id').inTable('propertyAdRealties').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.decimal('price').notNullable()

    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('sellable');
};
