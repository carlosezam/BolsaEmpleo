<?php

namespace app\models\ajax;

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
class Empresas extends \yii\db\ActiveRecord
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
            [['rfc', 'nombre', 'telefono', 'encargado', 'domicilio'], 'required'],
            [['telefono'], 'integer'],
            [['rfc'], 'string', 'max' => 20],
            [['nombre', 'encargado'], 'string', 'max' => 100],
            [['correo'], 'string', 'max' => 75],
            [['domicilio'], 'string', 'max' => 150],
            [['rfc'], 'unique'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'rfc' => 'Rfc',
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
        return $this->hasMany(Empleos::className(), ['id_empresa' => 'id']);
    }
}
