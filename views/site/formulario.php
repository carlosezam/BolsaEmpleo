<?php
use yii\helpers\Url;
use yii\helpers\Html;
?>

<h2>Formulario</h2>
<h3><?= $mensaje ?></h3>
<?= Html::beginForm(
            Url::toRoute('site/request'),
            'get',
            ['class' => 'form-inline']
        );
?>


<div class="form-group">
    <?= Html::label('introduce tu nombre', 'nombre')?>
    <?= Html::textInput('nombre', null, ['class'=>'form-control'])?>
</div>

<?= Html::submitButton('Enviar', ['class' => 'btn btn-primary'])?>

<?= Html::endForm(); ?>

