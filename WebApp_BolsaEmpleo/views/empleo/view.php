<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model app\models\Empleo */

$this->title = $model->id;
$this->params['breadcrumbs'][] = ['label' => Yii::t('app', 'Empleos'), 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="empleo-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a(Yii::t('app', 'Actualizar'), ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a(Yii::t('app', 'Eliminar'), ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => Yii::t('app', '¿Estás seguro de eliminar este registro?'),
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'puesto',
            'salario',
            'descripcion',
            'vacantes',
            'domicilio',
            [ 'label' => 'Empresa', 'value' => $model->empresa->nombre],
            [ 'label' => 'Municipio', 'value' => $model->municipio->nombre],
            [ 'label' => 'Activo', 'value' => $model->active ? 'si' : 'no'],

        ],
    ]) ?>

</div>
