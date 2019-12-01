+package by.training.security;
+
+import by.training.command.ActionCommand;
+import by.training.servlet.ServletRouter;
+
+import javax.servlet.http.HttpServletRequest;
+import javax.servlet.http.HttpServletResponse;
+
+public class ForSpecieficAccessChecker implements AccessChecker {
+
+
+    @Override
+    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {

         String playerId = request.getParameter(AttributesContainer.PLAYER_ID.toString())
+        HttpSession session = request.getSession();
+        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
+        PlayerDto playerToOperate = (PlayerDto) session.getAttribute(AttributesContainer.PLAYER_TO_OPERATE.toString());
+        return user != null && user.getPlayerId() == playerToOperate.getId();
+        }
+    
+}