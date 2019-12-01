+package by.training.security;
+
+import by.training.command.ActionCommand;
+import by.training.servlet.ServletRouter;
+
+import javax.servlet.http.HttpServletRequest;
+import javax.servlet.http.HttpServletResponse;
+
+public class ForNonPlayerAccessManager implements AccessManager {
+
+
+    @Override
+    public boolean isAccessAllowed(ActionCommand command, HttpServletRequest request, HttpServletResponse response) {
+        HttpSession session = request.getSession();
+        UserDto user = (User) session.getAttribute(AttributesContainer.USER.toString());
+        return user == null || user.getPlayerId == 0;
+        
+    
+}