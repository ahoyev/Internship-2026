# Public deployment on Render

This app runs as a Docker web service. It requires a Render account and a connection
to the GitHub repository. Use sample data: the current app has one shared roster
and no sign-in, so anyone with the public link can add, edit, or remove records.
Local team data is excluded from the repository and Docker image.

## Service settings

- Repository: `https://github.com/ahoyev/Internship-2026`
- Branch: `main`
- Root directory: `SwimTeamManagerWeb`
- Runtime: Docker
- Dockerfile path: `./Dockerfile`
- Health check path: `/`

Render supplies `PORT`; the app uses that port in hosting and 8080 locally.
The Docker build runs the tests and packages the existing Java 21 application.

## Storage choice

A free service can demonstrate the app, but its filesystem is temporary. Records
can disappear after a restart, redeploy, or idle shutdown. Do not use this setup
for records that must be retained.

To preserve records, use a paid service and attach a persistent disk at `/app/data`.
The app writes `/app/data/team-data.xml` and a previous-save `.bak` file there.
Use one service instance and back up the disk. Select a paid plan only after
reviewing and approving the provider's current charges.

## After deployment

Wait for Render to report the service live, then open its assigned HTTPS URL.
Check the dashboard, add a sample swimmer, mark attendance, edit the swimmer, and
confirm removal. For persistent hosting, verify records survive a service restart.
Update both repository READMEs with the verified public URL.

Public demo: https://swimteam.onrender.com/
Set SERVER_SERVLET_CONTEXT_PATH=/ for this public service to remove the local path prefix.
