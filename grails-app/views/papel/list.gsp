<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'papel.label', default: 'Papel')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-papel" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<g:sePermitido funcionalidade="Gerenciar Papéis">
    <div id="list-papel" class="content scaffold-list" role="main">
        <h1><g:message code="default.list.label" args="[entityName]"/></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="dateCreated"
                                  title="${message(code: 'papel.dateCreated.label', default: 'Date Created')}"/>

                <g:sortableColumn property="lastUpdated"
                                  title="${message(code: 'papel.lastUpdated.label', default: 'Last Updated')}"/>

                <g:sortableColumn property="nome" title="${message(code: 'papel.nome.label', default: 'Nome')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${papelInstanceList}" status="i" var="papelInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td><g:link action="show"
                                id="${papelInstance.id}">${fieldValue(bean: papelInstance, field: "dateCreated")}</g:link></td>

                    <td><g:formatDate date="${papelInstance.lastUpdated}"/></td>

                    <td>${fieldValue(bean: papelInstance, field: "nome")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>

        <div class="pagination">
            <g:paginate total="${papelInstanceTotal}"/>
        </div>
    </div>
</g:sePermitido>
</body>
</html>
