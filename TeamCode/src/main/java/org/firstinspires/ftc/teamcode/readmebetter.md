## Modules

Our SDK has a couple of non standard modules, Roadrunner and FTCDashboard.
While not necessary, the purpose of these modules are to make programming and tuning
easier, so you should try to be familiar with them.
Documentation for these modules can be found at the following links.
FTCDashboard: https://acmerobotics.github.io/ftc-dashboard/
RoadRunnerDocs: https://rr.brott.dev/docs/v1-0/installation/

## Github

Fetching branches from the repository:
1) git fetch --all 
   This fetches all branches from the repository to you local system
2) git branch -a
   This shows all local and upstream branches, and should look something like this:
     master
     remotes/origin/2025-2026_master
     remotes/origin/HEAD -> origin/master
     remotes/origin/master
3) git checkout {branch}
   replace {branch} with the name of the desired branch
   example: checking out "remotes/origin/2025-2026_master" 
   in the above example would look like this:
     git checkout 2025-2026_master

Everyone will need to have the 2025-2026_master branch locally, 
and should also have their own branch to make changes on


