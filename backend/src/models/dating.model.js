const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')


const Dating = sequelize.define('Dating',{
    id:{
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    dateDating:{
        type: DataTypes.DATE,
        allowNull: true
    },
    comment:{
        type:DataTypes.STRING,
        allowNull: true
    },
    note:{
        type:DataTypes.INTEGER,
        allowNull: false
    },
})

module.exports = Dating