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
    - **Write** laconic, minimal and elegant code that does the function.
    - **Format** with `scalafmt` and **Commit** code before start editing. Commit message should start with "!
      amend [AI]" followed by your intention.
    - **Test** at the end of each iteration.
    - **Report** number of successful and failed tests at the end of each task
    - **Run** MiMa checks on changed modules.
    - **Keep Spark deps Provided**; do not add Spark as compile dependency.
    - **Document** user-facing changes; add/adjust tests accordingly.

- **Don’t**
    - Don't use "???" or other placeholders
    - Don't use runtime type cast (e.g. `asInstanceOf`) unless you have a specific reason
    - Don’t change CI secrets or publish settings.
    - Don’t introduce heavy dependencies into core APIs.
    - Don't delete existing code or document, comment them out if necessary.
    - Don't delete or modify existing test cases.
    - Don't use JVM runtime reflection and language features that lack type safety, unless neccessary.
    - Don’t bypass the aggregate root projects; build/test the entire project.
    - Don't write experimental code outside test directory, always clean them up after to avoid breaking the project
      compilation
    - Don't create symlink
