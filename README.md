# hmrc-frontend-scaffold.g8

This repository is a [Giter8](http://www.foundweekends.org/giter8/) template to make building frontend services on the
[HMRC Multichannel Digital Tax Platform](https://hmrc.github.io) (MDTP) easier.

## Detailed Documentation

Please see the [wiki](https://github.com/hmrc/hmrc-frontend-scaffold.g8/wiki) for detailed documentation (currently a work in progress).  An example service built using these scaffolds will be available soon for reference.

## Where to get help

If you're thinking of using this scaffold to create a new service, or have any questions about it, please get in touch - drop us a line in our [Slack channel](https://hmrcdigital.slack.com/archives/team-scaffolders) (requires an HMRC slack account).

## What is it?

This repository is a template that generates a Scala Play! frontend service ready for use on the MDTP.  It brings in all of the core libraries you need, and includes some useful components, patterns and scaffolds to make solving common problems easier.

In particular, it makes it extremely quick and easy to create one-question-per-page user journeys, caching the user's answers as they go.

## What it isn't

It isn't a framework or a forms engine.  It just gives you a plain old Scala microservice which you can develop, iterate and change as you like.  While you'll get the most benefit by using the patterns and scaffolds as-is, you aren't tied in to them; you're free to use or ignore them as you choose.

It also doesn't try to solve _every_ problem.  It takes a lot of the grunt work out of creating screens, and makes it easy to wire them together into journeys; but you'll still need to do the wiring up, and of course handle any interactions with other microservices etc. that you need.

## Usage

### Before you start

Please drop by our [Slack channel](https://hmrcdigital.slack.com/archives/team-scaffolders) to say hi and see if we can offer you any support or guidance (requires an HMRC slack account).

You will need to have sbt version 0.13.13 or later installed.

### Creating a new service

To create a new service:
* Run `sbt new hmrc/hmrc-frontend-scaffold.g8` and supply a name, e.g. _example-frontend_.
* `cd` into the new directory, e.g. `cd example-frontend`
* Initialise a git repo and make an initial commit, e.g. `git init && git add . && git commit -m 'Initial commit'`
* Create a repository.yaml file and add the "repoVisibility: xxxxx" line to it (these can be copied from another HMRC repo - public one can be found in this repo), replace xxxxx with the actual string.

### Adding new pages using scaffolds

In your service's root directory is a hidden directory `.g8`, which contains all of the scaffolds available for you to use.  Each will add a new screen or suite of related screens into your service.

To use a scaffold, run `sbt` in interactive mode and issue the command `g8Scaffold scaffoldName`, e.g. `g8Scaffold yesNoPage`.  The scaffold will ask you for some inputs, and create some new files (e.g. the `yesNoPage` scaffold will create a controller, view, and some specs).

Exit out of `sbt` and run the bash script `migrate.sh` in the root directory of your service.  This will modify a couple of files in the service, including routes and messages.

## Contributing

If you’ve spotted an issue or thought of a feature that you’d like to contribute to the scaffold, please take a few minutes to review our [contribution process and guidelines](CONTRIBUTING.md) before you submit your request.

## License

This code is open source software licensed under the Apache 2.0 License.
