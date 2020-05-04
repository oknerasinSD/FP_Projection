<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Главная</a>
            </li>
            <li class="nav-item">
                <#if !user??>
                <a class="nav-link" href="/login">Авторизация/Регистрация</a>
            </#if>
            </li>
            <li class="nav-item">
                <#if user??>
                <a class="nav-link" href="/clients">Клиенты</a>
                </#if>
            </li>
            <#if user??>
            <li class="nav-item">
                <a class="nav-link" href="/user/profile">Профиль</a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Список пользователей</a>
                 </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3"><#if user??>${name}<#else>Гость</#if></div>
        <#if user??><@l.logout /></#if>
    </div>
</nav>