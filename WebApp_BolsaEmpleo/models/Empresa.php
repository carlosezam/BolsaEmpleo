<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "empresas".
 *
 * @property integer $id
 * @property string $rfc
 * @property string $nombre
 * @property string $telefono
 * @property string $correo
 * @property string $encargado
 * @property string $domicilio
 *
 * @property Empleos[] $empleos
 */
class Empresa extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'empresas';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['rfc', 'nombre', 'telefono', 'encargado', 'domicilio'], 'required', 'message' => 'campo requerido'],
            [['telefono'], 'string', 'max' => 15 ,'message' => 'longitud invalida', 'tooShort' => 'asd'],
            //[['telefono'], 'integer', 'message' => 'Solo se admiten numeros'],
            [['telefono'],
                'integer', 'min'=>1,
                'message' => 'Solo se admiten números',
                'tooSmall' => 'Solo se admiten números mayores a 0'
            ],
            [['rfc'], 'string', 'max' => 20, 'tooLong' => 'Máximo 20 carcateres'],
            [['nombre', 'encargado'], 'string', 'max' => 100, 'tooLong' => 'Máximo 100 carcateres'],
            [['correo'], 'string', 'max' => 75, 'tooLong' => 'Máximo 75 carcateres'],
            [['correo'], 'email', 'message' => 'Ingresa un correo valido'],
            [['domicilio'], 'string', 'max' => 150, 'tooLong' => 'Máximo 150 carcateres'],
            [['rfc'], 'unique', 'message' => 'Ya existe una empresa con este RFC'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'rfc' => 'RFC',
            'nombre' => 'Nombre',
            'telefono' => 'Telefono',
            'correo' => 'Correo',
            'encargado' => 'Encargado',
            'domicilio' => 'Domicilio',
        ];
    }


    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpleos()
    {
        return $this->hasMany(Empleo::className(), ['id_empresa' => 'id']);
    }

    public static function dropdown()
    {
        return static::find()
                    ->select(['nombre'])
                    ->indexBy('id')
                    ->column();
    }


}
