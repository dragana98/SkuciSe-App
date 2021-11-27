
exports.up = function (knex) {
    return knex.schema.createTable("leasable", (tbl) => {
        tbl.increments() //id primary key AI
        tbl.integer('property_ad_realty_id')
            .references('id').inTable('propertyAdRealties').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('property_ad_id')
            .references('id').inTable('propertyAd').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.decimal('price').notNullable()
        tbl.decimal('deposit').notNullable()

    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('leasable');
};
