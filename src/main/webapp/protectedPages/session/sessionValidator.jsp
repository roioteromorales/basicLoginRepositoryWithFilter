<%@ page import="com.roisoftstudio.login.servlets.validators.SessionValidator,com.roisoftstudio.Constants,java.util.Set" %>
<%
    SessionValidator sessionValidator = new SessionValidator();
    sessionValidator.validateSession(session, response, allowedRoles);

    String username = sessionValidator.getSessionUsername(session);
    Set<String> userRoles = (Set<String>) session.getAttribute(Constants.PARAMETER_ROLES);
%>