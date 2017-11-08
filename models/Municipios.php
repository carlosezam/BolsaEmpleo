<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "municipios".
 *
 * @property integer $id
 * @property string $nombre
 *
 * @property Empleos[] $empleos
 */
class Municipios extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'municipios';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['nombre'], 'required'],
            [['nombre'], 'string', 'max' => 100],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'nombre' => 'Nombre',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpleos()
    {
        return $this->hasMany(Empleos::className(), ['id_municipio' => 'id']);
    }

    public static function dropdown()
    {
        return static::find()
            ->select(['nombre'])
            ->indexBy('id')
            ->column();
    }
}
