<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model app\models\Empleo */

$this->title = Yii::t('app', 'Actualizar {modelClass}: ', [
    'modelClass' => 'Empleo',
]) . $model->puesto;
$this->params['breadcrumbs'][] = ['label' => Yii::t('app', 'Empleos'), 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = Yii::t('app', 'Actualizar');
?>
<div class="empleo-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
