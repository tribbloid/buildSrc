# Guardrails (Do/Don’t)

- **Initialize**: before any task, make sure the repository is properly configured:
    - **Configure MCP** in @`.mcp.json` under project root, using [MCP template](mcp.json) as a template. Create the
      file if
      it doesn't exist
    - **Configure Scala-MetaLS MCP** if @`.metals/mcp.json` exists, make sure the URL in @
      `.metals/mcp.json` is declared as an endpoint in @`.mcp.json`. Verify your work by connecting to it.
    - **Test** all MCP servers by connecting to them.

- **Do**
    - **Compile** often to verify your work.
    - **Write** larconic, minimal and elegant code that does the function.
    - **Commit** code to git local repository after each task, each commit message should start with your model
      name-version.
    - **Format** code with `scalafmt` before committing.
    - **Test** across supported Scala and Spark roots used in CI.
    - **Run** MiMa checks on changed modules.
    - **Keep Spark deps Provided**; do not add Spark as compile dependency.
    - **Document** user-facing changes; add/adjust tests accordingly.

- **Don’t**
    - Don’t change CI secrets or publish settings.
    - Don’t break binary compatibility without coordination and proper MiMa filters and versioning.
    - Don’t introduce heavy dependencies into core APIs.
    - Don't delete existing code and test cases.
    - Don't use JVM runtime reflection and language features that lack type safety, unless neccessary.
    - Don’t bypass the aggregate root projects; build/test the correct `root-sparkXX` target.
    - Don't write experimental code outside test directory, always clean them up after to avoid breaking the project
      compilation
