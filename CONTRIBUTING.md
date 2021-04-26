# Contributing

All contributors are welcome. Please use issues and pull requests to contribute to the project. And update [CHANGELOG.md](CHANGELOG.md) when committing.

## Making a Change

When you commit a change, please add a note to [CHANGELOG.md](CHANGELOG.md).

## Release Process

1. Confirm the build is not failing.
2. Push a release commit
   1. Update `versionCode` and `versionName`
   2. Create new version in the [CHANGELOG.md](CHANGELOG.md) and add all changes underneath it.
   3. Add a commit message like `version X.X.X`
3. Create a GitHub release
   1. Tag the release (like `X.X.X`)
   2. Paste notes from the [CHANGELOG.md](CHANGELOG.md)
