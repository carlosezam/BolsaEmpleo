<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
?>

<h3><?= $message ?></h3>

<h1>Registro</h1>
<?php
    $form = ActiveForm::begin([
        'method' => 'post',
        'id' => 'formulario',
        'enableClientValidation' => false,
        'enableAjaxValidation' => true,
    ]);
?>

<div class="form-group">
    <?= $form->field($model, 'username')->input('text') ?>
</div>
<div class="form-group">
    <?= $form->field($model, 'email')->input('email') ?>
</div>
<div class="form-group">
    <?= $form->field($model, 'password')->input('password') ?>
</div>
<div class="form-group">
    <?= $form->field($model, 'passwordConfirm')->input('password') ?>
</div>

<?= Html::submitButton('Registar', ['class'=>'btn btn-primary']) ?>

<?php $form->end() ?>
