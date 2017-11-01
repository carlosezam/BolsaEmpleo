<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model app\models\Empresa */

$this->title = Yii::t('app', 'Update {modelClass}: ', [
    'modelClass' => 'Empresa',
]) . $model->id;
$this->params['breadcrumbs'][] = ['label' => Yii::t('app', 'Empresas'), 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = Yii::t('app', 'Update');
?>
<div class="empresa-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?php \yii\widgets\Pjax::begin() ?>

    <?= $this->render('_form', [
        'model' => $model,
        'mode' => 'update'
    ]) ?>

    <?php \yii\widgets\Pjax::end() ?>

</div>
