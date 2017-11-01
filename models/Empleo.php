<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "empleos".
 *
 * @property integer $id
 * @property string $puesto
 * @property double $salario
 * @property string $descripcion
 * @property integer $vacantes
 * @property string $domicilio
 * @property integer $id_empresa
 *
 * @property Empresas $idEmpresa
 */
class Empleo extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'empleos';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [
                ['puesto', 'salario', 'descripcion', 'vacantes', 'id_empresa'],
                    'required', 'message' => 'Campo requerido'
            ],
            [
                ['salario'],
                    'number',   'message'=> 'El salario debe ser un número',
                    'min' => 0, 'tooSmall' => 'Ingrese una cantidad positiva'
            ],
            [
                ['vacantes', 'id_empresa'],
                    'integer', 'message' => 'El numero de vacantes dede ser un entero',
                    'min' => 0, 'tooSmall' => 'el numero de vacantes debe ser positivo'
            ],
            [
                ['puesto'],
                    'string', 'message' => 'El puestod debe ser un texto',
                    'length' => [5,50],
                        'tooShort' => 'Al menos 5 caracteres',
                        'tooLong' => 'Maximo 50 caracteres'
            ],
            [['descripcion', 'domicilio'], 'string', 'max' => 150],
            [['id_empresa'], 'exist', 'skipOnError' => true, 'targetClass' => Empresa::className(), 'targetAttribute' => ['id_empresa' => 'id']],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'puesto' => 'Puesto',
            'salario' => 'Salario',
            'descripcion' => 'Descripcion',
            'vacantes' => 'Vacantes',
            'domicilio' => 'Domicilio',
            'id_empresa' => 'Empresa',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpresa()
    {
        return $this->hasOne(Empresa::className(), ['id' => 'id_empresa']);
    }
}
